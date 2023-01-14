import styles from './EditJournal.module.css';
import { useState } from 'react';
import { ConfirmModal } from '../components/Modal.jsx';
import EditJournalForm from '../components/JournalSection/EditJournalForm';

const jounalDummy = {
  dateNoticeId: 1,
  dateNoticeTitle: '어....타이틀..',
  startTime: '1월10일 09:00',
  endTime: '1월10일 18:00',
  scheduleBody:
    '대법원장의 임기는 6년으로 하며, 중임할 수 없다. 대법원장은 국회의 동의를 얻어 대통령이 임명한다. 모든 국민은 학문과 예술의 자유를 가진다. 대통령은 전시·사변 또는 이에 준하는 국가비상사태에 있어서 병력으로써 군사상의 필요에 응하거나 공공의 안녕질서를 유지할 필요가 있을 때에는 법률이 정하는 바에 의하여 계엄을 선포할 수 있다.국정의 중요한 사항에 관한 대통령의 자문에 응하기 위하여 국가원로로 구성되는 국가원로자문회의를 둘 수 있다. 모든 국민은 주거의 자유를 침해받지 아니한다. 주거에 대한 압수나 수색을 할 때에는 검사의 신청에 의하여 법관이 발부한 영장을 제시하여야 한다. 국가의 세입·세출의 결산, 국가 및 법률이 정한 단체의 회계검사와 행정기관 및 공무원의 직무에 관한 감찰을 하기 위하여 대통령 소속하에 감사원을 둔다.',
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
