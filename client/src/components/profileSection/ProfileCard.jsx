import styles from './ProfileCard.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import defaultUser from '../../assets/defaultUser.png';
import { MdStar } from 'react-icons/md';

const ProfileCard = ({ user, QuestionModalHandler }) => {
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
        <ButtonNightBlue buttonHandler={QuestionModalHandler} text="문의하기" />
      </div>
    </div>
  );
};

ProfileCard.propTypes = {
  isAnnounceOn: PropTypes.func,
  QuestionModalHandler: PropTypes.func,
  user: PropTypes.object,
};

export default ProfileCard;
