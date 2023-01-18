import styles from './Admin.module.css';
import { MdEdit, MdDelete, MdAddCircle } from 'react-icons/md';
import { Link } from 'react-router-dom';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../recoil/modal.js';
import { useEffect, useState } from 'react';
import reIssueToken from '../util/reIssueToken';
import axios from 'axios';
import Profile from '../recoil/profile';

const Admin = () => {
  const [profiles, setProfiles] = useState([]);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const profileState = useRecoilValue(Profile);
  const resetProfile = useResetRecoilState(Profile);

  const getUserProfile = async () => {
    axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

    axios.defaults.headers.common['Authorization'] =
      sessionStorage.getItem('authorization') ||
      localStorage.getItem('authorization');
    await axios
      .get(
        `/profiles/${
          sessionStorage.getItem('userId') || localStorage.getItem('userId')
        }`
      )
      .then(({ data }) => {
        console.log(data);

        setProfiles(data.data);
      })
      .catch(({ response }) => {
        console.log(response.status);
        if (response.status === 403)
          reIssueToken(getUserProfile).catch(() => {
            console.log('reset');
            resetProfile();
            window.location.href = '/login';
          });
      });
  };

  const deleteHandler = async (id) => {
    axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

    axios.defaults.headers.common['Authorization'] =
      sessionStorage.getItem('authorization') ||
      localStorage.getItem('authorization');

    await axios
      .delete(`/profiles/details/${id}`)
      .then(() => {
        setModal({
          isOpen: true,
          modalType: 'handlerAlert',
          props: {
            text: '삭제가 완료되었습니다.',
            modalHandler: () => {
              location.reload();
            },
          },
        });
        console.log('삭제 완료!');
      })
      .catch(({ response }) => {
        console.log(response.status);
        if (response.status === 403)
          reIssueToken(getUserProfile).catch(() => {
            console.log('reset');
            deleteHandler();
            window.location.href = '/login';
          });
      });
  };

  useEffect(() => {
    getUserProfile();
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.title}>전체 프로필 관리</div>
        <div className={styles.profileContainer}>
          <span
            className={styles.profileCount}
          >{`전체 프로필 : ${profiles.length}/4`}</span>
          <ul className={styles.profiles}>
            {profiles.map((profile) => (
              <Link
                key={profile.profileId}
                to={`/${
                  profileState.userStatus === 'TUTOR' ? 'tutor' : 'tutee'
                }profile/${profile.profileId}`}
              >
                <li className={styles.profileBox}>
                  <div className={styles.iconsBox}>
                    <MdEdit
                      id={profile.profileId}
                      className={styles.mdEdit}
                      onClick={(e) => {
                        e.preventDefault();
                        setModal({
                          isOpen: true,
                          modalType: 'confirm',
                          props: {
                            text: `프로필 수정 페이지로 이동합니다.`,
                            modalHandler: () => {
                              resetModal();
                              window.location.href = `/editprofile/${profile.profileId}`;
                            },
                          },
                        });
                      }}
                    />
                    <MdDelete
                      className={styles.mdDelete}
                      onClick={(e) => {
                        e.preventDefault();
                        setModal({
                          isOpen: true,
                          modalType: 'confirm',
                          props: {
                            text: `프로필을 삭제 하시겠습니까?
                            해당 프로필과 관련된 내용이 모두 삭제됩니다.`,
                            modalHandler: () => {
                              deleteHandler(profile.profileId);
                            },
                          },
                        });
                      }}
                    />
                  </div>
                  <div className={styles.img}>
                    <img
                      alt="프로필 사진"
                      src={profile.url}
                      className={styles.profileImg}
                    />
                  </div>
                  <div className={styles.profileTextBox}>
                    <span className={styles.name}>{profile.name}</span>
                    <span className={styles.school}>{profile.school}</span>
                  </div>
                </li>
              </Link>
            ))}
            {profiles.length < 4 && (
              <Link to="/addprofile">
                <li className={styles.addBox}>
                  <MdAddCircle className={styles.addIcon} />
                  <span className={styles.addText}>프로필 추가하기</span>
                </li>
              </Link>
            )}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Admin;
