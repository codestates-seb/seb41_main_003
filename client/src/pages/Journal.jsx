import styles from './Journal.module.css';
import JournalForm from '../components/JournalSection/JournalForm';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import Loading from '../components/Loading';

const initialData = {
  dateNoticeTitle: '',
  startTime: '',
  endTime: '',
  schedule: {},
  notice: {},
  Homeworks: [],
};

const Journal = () => {
  const { dateNoticeId } = useLocation().state;
  const [userData, setUserData] = useState(initialData);

  useEffect(() => {
    axios
      .get(`/tutoring/date-notice/${dateNoticeId}`)
      .then(({ data: { data } }) => {
        const {
          dateNoticeTitle,
          startTime,
          endTime,
          schedule: { scheduleBody },
          notice: { noticeBody },
          homeworks,
        } = data;
        setUserData({
          dateNoticeTitle,
          startTime,
          endTime,
          scheduleBody,
          noticeBody,
          homeworks,
        });
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        {userData.startTime !== '' ? (
          <JournalForm userData={userData} />
        ) : (
          <Loading height="800px" />
        )}
      </div>
    </div>
  );
};
export default Journal;
