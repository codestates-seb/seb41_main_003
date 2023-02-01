import styles from './Tutoring.module.css';
import JournalList from '../components/Tutoring/JournalList';
import FinishedJournalList from '../components/Tutoring/FinishedJournalList';
import { useLocation } from 'react-router-dom';
import Profile from '../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import Loading from '../components/Loading';

const Tutoring = () => {
  const [tutoring, setTutoring] = useState({});
  const [pageInfo, setPageInfo] = useState({
    page: 1,
    size: 5,
    totalElements: 1,
    totalPages: 1,
  });

  const { profileId } = useRecoilValue(Profile);

  const tutoringId = useLocation().state.tutoringId;

  const getTutoringData = async () => {
    await axios
      .get(`tutoring/details/${profileId}/${tutoringId}`)
      .then(({ data }) => {
        setTutoring(data.data);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getTutoringData();
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.title}>과외 관리</div>
        <div className={styles.listContainer}>
          {Object.keys(tutoring).length !== 0 ? (
            tutoring.tutoringStatus === 'FINISH' ||
            tutoring.tutoringStatus.includes('DELETE') ? (
              <FinishedJournalList
                tutoring={tutoring}
                setTutoring={setTutoring}
                pageInfo={pageInfo}
                setPageInfo={setPageInfo}
              />
            ) : (
              <JournalList
                tutoring={tutoring}
                setTutoring={setTutoring}
                pageInfo={pageInfo}
                setPageInfo={setPageInfo}
              />
            )
          ) : (
            <Loading />
          )}
        </div>
      </div>
    </div>
  );
};
export default Tutoring;
