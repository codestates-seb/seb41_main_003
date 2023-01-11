import styles from './ProfileCard.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import defaultUser from '../../assets/defaultUser.png';
import { MdStar } from 'react-icons/md';

const ProfileCard = ({ user }) => {
  return (
    <div className={styles.cardContainer}>
      <img alt="user img" src={defaultUser} />
      <div className={styles.textContainer}>
        <div className={styles.starLine}>
          <div className={styles.font1}>{user.name}</div>
          <div className={styles.starBox}>
            <MdStar fill="#F0C24D" />
            <div className={styles.paragragh}>{user.rate}</div>{' '}
            <div className={styles.paragragh}>/ 5</div>
          </div>
        </div>
        <div>
          <div className={styles.font4}>한 줄 소개</div>
          <div className={styles.paragragh}>{user.bio}</div>
        </div>
        <div>
          <div className={styles.font4}>학교</div>
          <div className={styles.paragragh}>{user.school}</div>
        </div>
        <div>
          <div className={styles.font4}>과목</div>
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
      </div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue text="문의하기" />
      </div>
    </div>
  );
};
const MyProfileCard = ({ user, isAnnounceOn, modalOpenOnHandler }) => {
  return (
    <div className={styles.cardContainer}>
      <img alt="user img" src={defaultUser} />
      <div className={styles.textContainer}>
        <div className={styles.starLine}>
          <div className={styles.font1}>{user.name}</div>
          <div className={styles.starBox}>
            <MdStar fill="#F0C24D" />
            <div className={styles.paragragh}>{user.rate}</div>{' '}
            <div className={styles.paragragh}>/ 5</div>
          </div>
        </div>
        <div>
          <div className={styles.font4}>한 줄 소개</div>
          <div className={styles.paragragh}>{user.bio}</div>
        </div>
        <div>
          <div className={styles.font4}>학교</div>
          <div className={styles.paragragh}>{user.school}</div>
        </div>
        <div>
          <div className={styles.font4}>과목</div>
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
      </div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue text="수정하기" />
      </div>
      <>
        <div>
          <div className={styles.toggleContainer}>
            <div className={styles.toggleTextBox}>
              <div className={styles.firstText}>공고 상태</div>
              <div className={styles.secondText}>
                지금은{' '}
                {isAnnounceOn ? (
                  <span className={styles.announceOn}>공고 중</span>
                ) : (
                  <span>공고 안함</span>
                )}{' '}
                상태입니다
              </div>
            </div>
            {isAnnounceOn ? (
              <button
                className={styles.announceOnButton}
                onClick={modalOpenOnHandler}
              >
                {' '}
                <div className={styles.slider}></div>
              </button>
            ) : (
              <button
                className={styles.announceOffButton}
                onClick={modalOpenOnHandler}
              >
                <div className={styles.slider}></div>
              </button>
            )}
          </div>
        </div>
      </>
    </div>
  );
};
ProfileCard.propTypes = {
  isAnnounceOn: PropTypes.func,
  modalOpenOnHandler: PropTypes.func,
  user: PropTypes.object,
};
MyProfileCard.propTypes = {
  isAnnounceOn: PropTypes.func,
  modalOpenOnHandler: PropTypes.func,
  user: PropTypes.object,
};

export { ProfileCard, MyProfileCard };
