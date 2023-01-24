import styles from './ProfileCard.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import { useNavigate, useLocation } from 'react-router-dom';
import ModalState from '../../recoil/modal';
import { MdStar } from 'react-icons/md';
import axios from 'axios';
import Profile from '../../recoil/profile';

const ProfileCard = ({ user }) => {
  const { name, rate, bio, school, subjects, profileImage, profileStatus } =
    user;

  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);

  const { profileId } = useLocation().state;

  const myProfileId = useRecoilValue(Profile).profileId;
  const { userStatus } = useRecoilValue(Profile);

  const postData =
    userStatus === 'TUTOR'
      ? { tutorId: myProfileId, tuteeId: profileId }
      : { tutorId: profileId, tuteeId: myProfileId };

  const navigate = useNavigate();

  const postNewMessageRoom = async () => {
    await axios
      .post(`/messages/${myProfileId}`, postData)
      .then(() => navigate(`/message`))
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '상대방에게 문의를 요청하시겠습니까?',
      modalHandler: () => {
        postNewMessageRoom();
        navigate(`/message`);
        reset();
      },
    },
  };

  return (
    <div className={styles.cardContainer}>
      <img alt="user img" src={profileImage?.url} />
      <section className={styles.textContainer}>
        <div className={styles.starLine}>
          <h3 className={styles.font1}>{name}</h3>
          {profileStatus === 'TUTOR' && (
            <div className={styles.starBox}>
              <MdStar fill="#F0C24D" size="21" />
              <p className={styles.paragragh}>{rate} / 5 </p>
            </div>
          )}
        </div>
        <div>
          <p className={styles.font4}>한 줄 소개</p>
          <p className={styles.paragragh}>{bio}</p>
        </div>
        <div>
          <p className={styles.font4}>학교</p>
          <p className={styles.paragragh}>{school}</p>
        </div>
        <div>
          <p className={styles.font4}>과목</p>
          <span className={styles.subjects}>
            <ul>
              {subjects.map(({ subjectId, subjectTitle }) => {
                return <BlueSubject key={subjectId} text={subjectTitle} />;
              })}
            </ul>
          </span>
        </div>
      </section>
      {Number(profileId) !== myProfileId &&
        !location.pathname.includes(userStatus.toLowerCase()) && (
          <div className={styles.buttonBox}>
            <ButtonNightBlue
              buttonHandler={(e) => {
                e.preventDefault();
                setModal(confirm);
              }}
              text="문의하기"
            />
          </div>
        )}
    </div>
  );
};

ProfileCard.propTypes = {
  user: PropTypes.object,
};

export default ProfileCard;
