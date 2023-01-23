import styles from './ChangeJournal.module.css';
import { useEffect } from 'react';
import EditJournalForm from '../components/JournalSection/EditJournalForm';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useSetRecoilState } from 'recoil';
import ChangeJournal from '../recoil/journal';

const EditJournal = () => {
  const setUserData = useSetRecoilState(ChangeJournal);
  const { dateNoticeId } = useParams();

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
        <EditJournalForm />
      </div>
    </div>
  );
};

export default EditJournal;
