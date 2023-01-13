import styles from './Journal.module.css';
import { ConfirmModal } from '../components/Modal.jsx';
import JournalForm from '../components/JournalSection/JournalForm';
import { useState } from 'react';

const jounalDummy = {
  dateNoticeId: 1,
  dateNoticeTitle: '어....타이틀..',
  startTime: '1월10일 09:00',
  endTime: '1월10일 18:00',
  scheduleBody:
    '대법원장의 임기는 6년으로 하며, 중임할 수 없다. 대법원장은 국회의 동의를 얻어 대통령이 임명한다. 모든 국민은 학문과 예술의 자유를 가진다. 대통령은 전시·사변 또는 이에 준하는 국가비상사태에 있어서 병력으로써 군사상의 필요에 응하거나 공공의 안녕질서를 유지할 필요가 있을 때에는 법률이 정하는 바에 의하여 계엄을 선포할 수 있다.',
  noticeBody:
    '모든 국민은 직업선택의 자유를 가진다. 이 헌법중 공무원의 임기 또는 중임제한에 관한 규정은 이 헌법에 의하여 그 공무원이 최초로 선출 또는 임명된 때로부터 적용한다. 국방상 또는 국민경제상 긴절한 필요로 인하여 법률이 정하는 경우를 제외하고는, 사영기업을 국유 또는 공유로 이전하거나 그 경영을 통제 또는 관리할 수 없다. 대통령의 국법상 행위는 문서로써 하며, 이 문서에는 국무총리와 관계 국무위원이 부서한다. 군사에 관한 것도 또한 같다.',
  subjects: [
    { subjectId: 1, subjectTitle: '영어', content: '영어 잘합니다' },
    { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
  ],
  Homeworks: [
    {
      homeworkId: 1,
      homeworkBody: '과제 1',
      HomeworkStatus: true,
    },
    {
      homeworkId: 2,
      homeworkBody: '과제 2',
      HomeworkStatus: false,
    },
    {
      homeworkId: 3,
      homeworkBody: '과제 3',
      HomeworkStatus: 'true',
    },
    {
      homeworkId: 4,
      homeworkBody: '과제 4',
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
