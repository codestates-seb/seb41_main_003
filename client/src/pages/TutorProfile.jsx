import styles from './TutorProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import DummyData from '../components/profileSection/DummyData';
const TutorProfile = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <div className={styles.container}>
          <ProfileCard user={DummyData} />
          <ProfileContents user={DummyData} />
        </div>
      </div>
    </div>
  );
};

export default TutorProfile;
