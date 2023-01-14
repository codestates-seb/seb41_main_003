import styles from './MyProfileCard.module.css';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import defaultUser from '../../assets/defaultUser.png';
import { MdStar } from 'react-icons/md';
import Toggle from './Toggle';

const MyProfileCard = ({ user, isAnnounceOn, modalOpenOnHandler }) => {
  return (
    <div className={styles.cardContainer}>
      <img alt="user img" src={defaultUser} />
      <section className={styles.textContainer}>
        <div className={styles.starLine}>
          <p className={styles.font1}>{user.name}</p>
          <div className={styles.starBox}>
            <MdStar fill="#F0C24D" size="21" />
            <p className={styles.paragragh}>{user.rate} / 5 </p>
          </div>
        </div>
        <div>
          <p className={styles.font4}>한 줄 소개</p>
          <p className={styles.paragragh}>{user.bio}</p>
        </div>
        <div>
          <p className={styles.font4}>학교</p>
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
        <Link to="/pages/EditProfile">
          <ButtonNightBlue text="수정하기" />
        </Link>
      </div>
      <section>
        <div className={styles.toggleContainer}>
          <div className={styles.toggleTextBox}>
            <p className={styles.announceText1}>공고 상태</p>
            <div className={styles.announceText2}>
              지금은{' '}
              {isAnnounceOn === 'REQUEST' ? (
                <span className={styles.announceOnText}>공고 중</span>
              ) : (
                <span>공고 안함</span>
              )}{' '}
              상태입니다
            </div>
          </div>
          <Toggle
            isAnnounceOn={isAnnounceOn}
            modalOpenOnHandler={modalOpenOnHandler}
          />
        </div>
      </section>
    </div>
  );
};
MyProfileCard.propTypes = {
  isAnnounceOn: PropTypes.string,
  modalOpenOnHandler: PropTypes.func,
  user: PropTypes.object,
  setIsAnnounceOn: PropTypes.func,
  setIsModalOpen: PropTypes.func,
};

export default MyProfileCard;
