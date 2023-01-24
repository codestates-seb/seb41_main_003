import styles from './TuteeProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';

const TuteeProfile = () => {
  const { tuteeProfileId } = useLocation().state;
  const [profileDetail, setProfileDetail] = useState({
    profileId: 0,
    userId: 0,
    name: '',
    rate: 0,
    bio: '',
    wantDate: '',
    pay: '',
    way: '',
    profileStatus: '',
    wantedStatus: '',
    gender: '',
    preTutoring: '',
    difference: '',
    school: '',
    character: '',
    subjects: [],
    reviews: [],
  });

  const getProfileData = async () => {
    await axios
      .get(`/profiles/details/${tuteeProfileId}`)
      .then((res) => {
        setProfileDetail(res.data.data);
        console.log(res.data.data);
      })
      .catch((err) => console.log(err.status));
  };

  useEffect(() => {
    getProfileData();
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ProfileCard user={profileDetail} />
        <ProfileContents user={profileDetail} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default TuteeProfile;
