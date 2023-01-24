import styles from './Admin.module.css';
import { MdEdit, MdDelete, MdAddCircle } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import { useSetRecoilState, useResetRecoilState, useRecoilState } from 'recoil';
import ModalState from '../recoil/modal.js';
import { useEffect, useState } from 'react';
import axios from 'axios';
import Profile from '../recoil/profile';

const Admin = () => {
  const [profilesData, setProfilesData] = useState([]);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const [profile, setProfile] = useRecoilState(Profile);
  const navigate = useNavigate();

  const getUserProfile = async () => {
    await axios
      .get(`/profiles/${sessionStorage.getItem('userId')}`)
      .then(({ data }) => {
        console.log(data);

        setProfilesData(data.data);
      })
      .catch((err) => {
        console.log(err.response.status);
        console.log(err.response.data.message);
      });
  };

  const deleteHandler = async (id) => {
    await axios
      .delete(`/profiles/details/${id}`)
      .then(() => {
        setModal({
          isOpen: true,
          modalType: 'handlerAlert',
          props: {
            text: '삭제가 완료되었습니다.',
            modalHandler: () => {
              setProfile((prev) => ({ ...prev, profileId: 0 }));
              location.reload();
            },
          },
        });
        console.log('삭제 완료!');
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
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
          >{`전체 프로필 : ${profilesData.length}/4`}</span>
          <ul className={styles.profiles}>
            {profilesData.map((obj) => (
              <Link
                key={obj.profileId}
                to={`/${
                  profile.userStatus === 'TUTOR' ? 'tutor' : 'tutee'
                }profile`}
                state={{ profileId: obj.profileId }}
              >
                <li className={styles.profileBox}>
                  <div className={styles.iconsBox}>
                    <MdEdit
                      id={obj.profileId}
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
                              navigate(`/editprofile`, {
                                state: { profileId: obj.profileId },
                              });
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
                              deleteHandler(obj.profileId);
                            },
                          },
                        });
                      }}
                    />
                  </div>
                  <div className={styles.img}>
                    <img
                      alt="프로필 사진"
                      src={obj.url}
                      className={styles.profileImg}
                    />
                  </div>
                  <div className={styles.profileTextBox}>
                    <span className={styles.name}>{obj.name}</span>
                    <span className={styles.school}>{obj.school}</span>
                  </div>
                </li>
              </Link>
            ))}
            {profilesData.length < 4 && (
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
