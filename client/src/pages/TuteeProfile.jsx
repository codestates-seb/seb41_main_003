import styles from './TuteeProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Loading from '../components/Loading';

const TuteeProfile = () => {
  const { profileId } = useLocation().state;
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
      .get(`/profiles/details/${profileId}`)
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
      {profileDetail.profileId !== 0 ? (
        <div className={styles.container}>
          <ProfileCard user={profileDetail} />
          <ProfileContents user={profileDetail} />
        </div>
      ) : (
        <Loading height="720px" />
      )}

      <ButtonTop />
    </div>
  );
};

export default TuteeProfile;
