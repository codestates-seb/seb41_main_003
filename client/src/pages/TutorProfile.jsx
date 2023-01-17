import styles from './TutorProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const TutorProfile = () => {
  const { profileId } = useParams();
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
  const token = sessionStorage.getItem('authorization');

  const getProfileData = async () => {
    await axios
      .get(`${process.env.REACT_APP_BASE_URL}/profiles/details/${profileId}`, {
        headers: { Authorization: token },
      })
      .then((res) => {
        setProfileDetail(res.data.data);
        console.log(res.data.data);
      })
      .catch((err) => console.log(err));
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

export default TutorProfile;
