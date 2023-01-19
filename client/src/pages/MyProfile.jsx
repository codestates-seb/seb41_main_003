import styles from './MyProfile.module.css';
import { useEffect, useState } from 'react';
import { ProfileContents, MyProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import Profile from '../recoil/profile';
const MyProfile = () => {
  const { profileId } = useRecoilValue(Profile);
  const [user, setUser] = useState({
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
    profileImage: {},
  });

  const [pageInfo, setPageInfo] = useState({
    page: 0,
    size: 5,
    totalElements: 0,
    totalPages: 0,
  });

  const [page, setPage] = useState(0);

  const getProfileData = async () => {
    await axios
      .get(`/profiles/details/${profileId}?page=${page}`)
      .then((res) => {
        setUser(res.data.data);
        setPageInfo(res.data.pageInfo);
      })
      .catch((err) => console.log(err.status));
  };

  useEffect(() => {
    getProfileData();
  }, [page]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <MyProfileCard user={user} setUser={setUser} />
        <ProfileContents user={user} pageInfo={pageInfo} setPage={setPage} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default MyProfile;
