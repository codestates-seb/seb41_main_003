import styles from './MyProfileCard.module.css';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import { MdStar, MdDelete } from 'react-icons/md';
import Toggle from './Toggle';
import { useSetRecoilState, useResetRecoilState, useRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import Profile from '../../recoil/profile';
import axios from 'axios';

const MyProfileCard = ({ user, setUser }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const navigate = useNavigate();
  const [{ profileId }, setProfile] = useRecoilState(Profile);
  const userStatus = sessionStorage.getItem('userStatus');

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '프로필 수정 페이지로 이동 하시겠습니까?',
      modalHandler: () => {
        navigate(`/editprofile`, {
          state: { profileId },
        });
        resetModal();
      },
    },
  };

  const getProfileData = async () => {
    await axios
      .get(`/profiles/details/${profileId}`)
      .then(({ data }) => {
        setUser(data.data);
        setProfile((prev) => ({ ...prev, url: data.data.profileImage.url }));
      })
      .catch((err) => console.log(err.status));
  };

  const deleteHandler = async () => {
    axios
      .delete(`/upload/profile-image/${profileId}`)
      .then(() => {
        getProfileData();
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const deleteProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '프로필 이미지를 삭제하시겠습니까?',
      modalHandler: () => {
        deleteHandler();
        resetModal();
      },
    },
  };

  return (
    <div className={styles.cardContainer}>
      <div className={styles.userImage}>
        <img alt="user img" src={user.profileImage.url} />
        {user.profileImage.url !==
          'https://image-test-suyoung.s3.ap-northeast-2.amazonaws.com/image/user.png' && (
          <button
            type="button"
            onClick={() => {
              setModal(deleteProps);
            }}
          >
            <MdDelete />
          </button>
        )}
      </div>

      <section className={styles.textContainer}>
        <div
          className={`${styles.starLine}  ${
            user.name.length >= 6 ? styles.overName : ''
          }`}
        >
          <p
            className={` ${user.name.length > 4 ? styles.font2 : styles.font1}`}
          >
            {user.name}
          </p>
          {userStatus === 'TUTOR' && (
            <div className={styles.starBox}>
              <MdStar fill="#F0C24D" size="21" />
              <p className={styles.paragragh}>{user.rate} / 5 </p>
            </div>
          )}
        </div>
        <div>
          <p className={styles.font4}>한 줄 소개</p>
          <p className={styles.paragragh}>{user.bio}</p>
        </div>
        <div>
          <p className={styles.font4}>
            {userStatus === 'TUTOR' ? '학교 , 학번' : '학년'}
          </p>
          <p className={styles.paragragh}>{user.school}</p>
        </div>
        <div>
          <p className={styles.font4}>과목</p>
          <span className={styles.subjects}>
            <ul>
              {user.subjects.map((subject) => {
                return (
                  <BlueSubject
                    key={subject.subjectId}
                    text={subject.subjectTitle}
                  />
                );
              })}
            </ul>
          </span>
        </div>
      </section>
      <div className={styles.buttonBox}>
        <ButtonNightBlue
          text="수정하기"
          buttonHandler={() => setModal(confirm)}
        />
      </div>
      <section>
        {user.wantedStatus === 'BASIC' ? (
          <div className={styles.toggleContainer}>
            <div className={styles.toggleTextBox}>
              <p className={styles.announceText1}>공고 상태</p>
              <div className={styles.announceText2}>
                공고 상태 수정은 프로필 필수 항목 작성이 완료되면 가능합니다.
              </div>
            </div>
          </div>
        ) : (
          <div className={styles.toggleContainer}>
            <div className={styles.toggleTextBox}>
              <p className={styles.announceText1}>공고 상태</p>
              <div className={styles.announceText2}>
                지금은{' '}
                {user.wantedStatus === 'REQUEST' ? (
                  <span className={styles.announceOnText}>공고 중</span>
                ) : (
                  <span>공고 안함</span>
                )}{' '}
                상태입니다
              </div>
            </div>
            <Toggle user={user} setUser={setUser} />
          </div>
        )}
      </section>
    </div>
  );
};
MyProfileCard.propTypes = {
  user: PropTypes.object,
  setUser: PropTypes.func,
};

export default MyProfileCard;
