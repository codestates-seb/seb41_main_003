import styles from './MyProfile.module.css';
import { useState } from 'react';
import { ProfileContents, MyProfileCard } from '../components/profileSection';
import DummyData from '../components/profileSection/DummyData';
import { ButtonTop } from '../components/Button';

const MyProfile = () => {
  const [user, setUser] = useState(DummyData);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <MyProfileCard user={user} setUser={setUser} />
        <ProfileContents user={user} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default MyProfile;
