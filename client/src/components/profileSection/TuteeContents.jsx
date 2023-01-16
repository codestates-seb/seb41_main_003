import styles from './ProfileContents.module.css';
import PropType from 'prop-types';

const TuteeContents = ({ user }) => {
  const { way, gender, character, pay, wantDate, preTutoring } = user;

  return (
    <section className={styles.container}>
      <div>
        <p className={styles.font4}>성격</p>
        <p className={styles.paragragh}>{character}</p>
      </div>
      <div>
        <p className={styles.font4}>성별</p>
        <p className={styles.paragragh}>{gender}</p>
      </div>

      <div>
        <p className={styles.font4}>예산</p>
        <p className={styles.paragragh}>{pay}</p>
      </div>
      <div>
        <p className={styles.font4}>과외 가능 요일 / 시간대</p>
        <p className={styles.paragragh}>{wantDate}</p>
      </div>
      <div>
        <p className={styles.font4}>선생님께 바라는 점</p>
        <p className={styles.paragragh}>{way}</p>
      </div>
      <div>
        <p className={styles.font4}>시범 과외 가능 여부</p>
        <p className={styles.paragragh}>{preTutoring}</p>
      </div>
    </section>
  );
};
TuteeContents.propTypes = {
  user: PropType.object,
};

export default TuteeContents;
