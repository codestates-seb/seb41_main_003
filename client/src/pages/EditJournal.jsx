import styles from './EditJournal.module.css';
import { useState } from 'react';
import { ConfirmModal } from '../components/Modal.jsx';
import EditJournalForm from '../components/JournalSection/EditJournalForm';
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

const EditJournal = () => {
  const [user, setUser] = useState(jounalDummy);
  const [isConfirm, setIsConfirm] = useState(false);
  const confirmText = `취소하시겠습니까?
  작성 중인 내용이 모두 사라집니다.`;

  const confirmHandler = (e) => {
    if (e.target.name === 'yes') {
      setIsConfirm((prev) => !prev);
      // TODO: 서버에 수정된 데이터 PATCH (특정 날짜 일지 수정)요청
      // TODO: 과외일지 상세페이지로 이동
    } else {
      setIsConfirm((prev) => !prev);
    }
  };
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <EditJournalForm
          user={user}
          setUser={setUser}
          confirmHandler={confirmHandler}
        />
      </div>
      {isConfirm && (
        <ConfirmModal text={confirmText} modalHandler={confirmHandler} />
      )}
    </div>
  );
};

export default EditJournal;
