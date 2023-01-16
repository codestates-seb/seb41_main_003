import styles from './TuteeProfile.module.css';
import { useState, useCallback } from 'react';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ConfirmModal } from '../components/modal/DefaultModal.jsx';
import DummyData from '../components/profileSection/DummyData';
import { ButtonTop } from '../components/Button';

const TuteeProfile = () => {
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

export default TuteeProfile;
