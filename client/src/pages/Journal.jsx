import styles from './Journal.module.css';
import { ConfirmModal } from '../components/Modal.jsx';
import DummyData from '../components/profileSection/DummyData';
import JournalForm from '../components/JournalSection/JournalForm';
import { useState } from 'react';

const jounalDummy = {
  dateNoticeId: 1,
  dateNoticeTitle: '어....타이틀..',
  startTime: '1월10일 09:00',
  endTime: '1월10일 18:00',
  scheduleBody: '계획몸',
  noticeBody: '공지몸',
  subjects: [
    { subjectId: 1, subjectTitle: '영어', content: '영어 잘합니다' },
    { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
  ],
  Homeworks: [
    {
      homeworkId: 1,
      homeworkBody: '과제몸1',
      HomeworkStatus: true,
    },
    {
      homeworkId: 2,
      homeworkBody: '과제몸2',
      HomeworkStatus: false,
    },
    {
      homeworkId: 3,
      homeworkBody: '과제몸3',
      HomeworkStatus: 'true',
    },
    {
      homeworkId: 4,
      homeworkBody: '과제몸4',
      HomeworkStatus: 'false',
    },
  ],
};
const Journal = () => {
  const [user, setUser] = useState(jounalDummy);
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <JournalForm user={user} setUser={setUser} />
      </div>
    </div>
  );
};
export default Journal;
