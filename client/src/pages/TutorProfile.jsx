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
        setProfileDetail(res.data.data);
        setPageInfo(res.data.pageInfo);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getProfileData();
  }, [page]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <ProfileCard user={profileDetail} />
        <ProfileContents
          user={profileDetail}
          setPage={setPage}
          pageInfo={pageInfo}
        />
      </div>
      <ButtonTop />
    </div>
  );
};

export default TutorProfile;
