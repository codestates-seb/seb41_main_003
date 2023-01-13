import styles from './Tutoring.module.css';
import { Link } from 'react-router-dom';
import PropType from 'prop-types';

const Tutoring = ({ tutoring }) => {
  const {
    tutoringTitle,
    tutorName,
    tuteeName,
    tutoringStatus,
    createAt,
    updateAt,
  } = tutoring;

  return (
    <li className={styles.tutoring}>
      {/* // TODO : tutorlingId를 토대로 파라미터 추가하기 */}
      <Link to="/tutoring">
        <div>
          {/* 
          {<h4>
          // TODO : 회원 정보를 기반으로 튜터인지 튜티인지 확인해서 다르게 출력하기
            {userStatus === 'TUTOR ? tuteeName : tutorName} | {tutoringTitle}
          </h4>}
           */}
          <h4>
            {tuteeName} | {tutoringTitle}
          </h4>
          <span>
            {new Date(createAt).toLocaleDateString()} ~{' '}
            {tutoringStatus === 'FINISH' &&
              new Date(updateAt).toLocaleDateString()}
          </span>
          {tutoringStatus === 'UNCHECK' && <span className={styles.noti} />}
        </div>
      </Link>
    </li>
  );
};

Tutoring.propTypes = {
  tutoring: PropType.object,
};

export default Tutoring;
