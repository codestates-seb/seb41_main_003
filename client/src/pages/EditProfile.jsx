import ChangeProfileCard from '../components/ChangeProfile/ChangeProfileCard';
import ChangeProfileContents from '../components/ChangeProfile/ChangeProfileContents';
import styles from './ChangeProfile.module.css';
import { ButtonTop } from '../components/Button';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const EditProfile = () => {
  const { profileId } = useParams();
  const [profileData, setProfileData] = useState({
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
    profileImage: { url: '' },
  });
  const token = sessionStorage.getItem('authorization');

  const getProfileData = async () => {
    await axios
      .get(`${process.env.REACT_APP_BASE_URL}/profiles/details/${profileId}`, {
        headers: { Authorization: token },
      })
      .then((res) => {
        setProfileData(res.data.data);
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
        <ChangeProfileCard
          user={profileData}
          setUser={setProfileData}
          isNew={false}
        />
        <ChangeProfileContents user={profileData} setUser={setProfileData} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default EditProfile;
