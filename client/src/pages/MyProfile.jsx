import styles from './MyProfile.module.css';
import { useEffect, useState } from 'react';
import { ProfileContents, MyProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const MyProfile = () => {
  //TODO: useParams 말고 저장되어 있는 myprofileId를 사용해야 할 듯
  // const { profileId } = useRecoilValue(Profile);
  const { profileId } = useParams();
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
      .get(
        `${process.env.REACT_APP_BASE_URL}/profiles/details/${profileId}?page=${page}`,
        {
          headers: {
            Authorization:
              sessionStorage.getItem('authorization') ||
              localStorage.getItem('authorization'),
          },
        }
      )
      .then((res) => {
        setUser(res.data.data);
        console.log(res.data.pageInfo);
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
