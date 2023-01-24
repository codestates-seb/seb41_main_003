import styles from './Journal.module.css';
import JournalForm from '../components/JournalSection/JournalForm';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

const initialData = {
  dateNoticeTitle: '',
  startTime: '',
  endTime: '',
  schedule: {},
  notice: {},
  subjects: [],
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
        <JournalForm userData={userData} />
      </div>
    </div>
  );
};
export default Journal;
