import styles from './MyProfile.module.css';
import { useState } from 'react';
import { ProfileContents, MyProfileCard } from '../components/profileSection';
import DummyData from '../components/profileSection/DummyData';
import { ButtonTop } from '../components/Button';

const MyProfile = () => {
  const [user, setUser] = useState(DummyData);
  const [isAnnounceOn, setIsAnnounceOn] = useState(DummyData.wantedStatus);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <MyProfileCard
          user={user}
          isAnnounceOn={isAnnounceOn}
          setIsAnnounceOn={setIsAnnounceOn}
        />
        <ProfileContents user={user} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default MyProfile;
