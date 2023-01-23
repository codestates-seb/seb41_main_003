import styles from './Tutoring.module.css';
import JournalList from '../components/Tutoring/JournalList';
import FinishedJournalList from '../components/Tutoring/FinishedJournalList';
import { useParams } from 'react-router-dom';
import Profile from '../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

const Tutoring = () => {
  const [tutoring, setTutoring] = useState({
    tutoringId: 0,
    tutoringTitle: '',
    tutoringStatus: 'PROGRESS',
    latestNoticeId: 0,
    latestNoticeBody: '',
    tuteeId: 0,
    tuteeName: '',
    tutorId: 0,
    tutorName: '',
    createAt: '',
    updateAt: '',
    dateNotices: [
      {
        dateNoticeId: 1,
        dateNoticeTitle: '',
        startTime: '',
        endTime: '',
        homeworkCount: 0,
        finishHomeworkCount: 0,
        noticeStatus: '',
      },
    ],
  });
  const [pageInfo, setPageInfo] = useState({
    page: 1,
    size: 5,
    totalElements: 1,
    totalPages: 1,
  });

  const { profileId } = useRecoilValue(Profile);
  const { tutoringId } = useParams();

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
        {tutoring.tutoringStatus === 'FINISH' ? (
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
        )}
      </div>
    </div>
  );
};
export default Tutoring;
