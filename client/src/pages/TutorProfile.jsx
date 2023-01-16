import styles from './TutorProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import DummyData from '../components/profileSection/DummyData';
import { ButtonTop } from '../components/Button';

const TutorProfile = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ProfileCard user={DummyData} />
        <ProfileContents user={DummyData} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default TutorProfile;
