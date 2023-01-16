import styles from './Admin.module.css';
import defaultUser from '../assets/defaultUser.png';
import { MdEdit, MdDelete, MdAddCircle } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../recoil/modal.js';

const profileLists = [
  {
    profileId: 1,
    name: '유영민',
    url: defaultUser,
    school: '서울대학교',
  },
  {
    profileId: 2,
    name: '신승구',
    url: defaultUser,
    school: '서울대학교',
  },
  {
    profileId: 3,
    name: '김민경',
    url: defaultUser,
    school: '서울대학교',
  },
];

const Admin = () => {
  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);
  const navigate = useNavigate();

  const confirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `프로필을 삭제 하시겠습니까?
      해당 프로필과 관련된 내용이 모두 삭제됩니다.`,
      modalHandler: () => {
        console.log('프로필 삭제 요청이 갑니다~');
        reset();
        //TODO : 프로필 삭제 요청
      },
    },
  };

  const confirmEditProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `프로필 수정 페이지로 이동합니다.`,
      modalHandler: () => {
        reset();
        navigate('/editprofile');
      },
    },
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.title}>전체 프로필 관리</div>
        <div className={styles.profileContainer}>
          <span
            className={styles.profileCount}
          >{`전체 프로필 : ${profileLists.length}/4`}</span>
          <ul className={styles.profiles}>
            {profileLists.map((el) => (
              <Link key={el.profileId} to="/tuteeprofile">
                <li className={styles.profileBox}>
                  <div className={styles.iconsBox}>
                    <MdEdit
                      id={el.profileId}
                      className={styles.mdEdit}
                      onClick={(e) => {
                        e.preventDefault();
                        setModal(confirmEditProps);
                      }}
                    />
                    <MdDelete
                      className={styles.mdDelete}
                      onClick={(e) => {
                        e.preventDefault();
                        setModal(confirmProps);
                      }}
                    />
                  </div>
                  <div className={styles.img}>
                    <img
                      alt="프로필 사진"
                      src={el.url}
                      className={styles.profileImg}
                    />
                  </div>
                  <div className={styles.profileTextBox}>
                    {/* TODO: 튜터/튜티 여부 포함하는 데이터에 따라 Link 경로의 분기 필요 */}
                    <span className={styles.name}>{el.name}</span>
                    <span className={styles.school}>{el.school}</span>
                  </div>
                </li>
              </Link>
            ))}
            {profileLists.length < 4 && (
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
