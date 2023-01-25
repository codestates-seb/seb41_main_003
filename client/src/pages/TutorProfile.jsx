import styles from './TutorProfile.module.css';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ButtonTop } from '../components/Button';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Loading from '../components/Loading';

const TutorProfile = () => {
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
      {profileDetail.profileId !== 0 ? (
        <div className={styles.container}>
          <ProfileCard user={profileDetail} />
          <ProfileContents
            user={profileDetail}
            setPage={setPage}
            pageInfo={pageInfo}
          />
        </div>
      ) : (
        <Loading height="720px" />
      )}

      <ButtonTop />
    </div>
  );
};

export default TutorProfile;
