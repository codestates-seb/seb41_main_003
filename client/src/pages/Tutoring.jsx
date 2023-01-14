import styles from './Tutoring.module.css';
import JournalList from '../components/Tutoring/JournalList';
import FinishedJournalList from '../components/Tutoring/FinishedJournalList';
import dummyTutoringData from '../components/Tutoring/dummyTutoringData';

//TODO: 과외 관리 페이지는 하나이지만 종료된 과외 관리 & 진행중인 과외 관리 컴포넌트가 각각 만들어져 있음
//종료된 과외를 관리하러 들어오느냐, 진행중인 과외를 관리하러 들어오느냐에 따라 분기해서 컴포넌트를 보여주어야 함 (현재는 더미 데이터 이용해 분기함)
//tutoringStatus를 통해서 진행중인 과외와 종료된 과외를 구분할 수 있음 (진행 중 : UNCHECKED & PROGRESS, 종료 : FINISHED )
//사용자가 튜터이냐 튜티이냐에 대한 분기는 (아마도) 로컬 스토리지에 저장될 user Status를 이용해 컴포넌트 안에서 합니다.

const Tutoring = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.title}>과외 관리</div>
        {dummyTutoringData.tutoringStatus === 'FINISHED' && (
          <FinishedJournalList />
        )}
        {(dummyTutoringData.tutoringStatus === 'PROGRESS' ||
          dummyTutoringData.tutoringStatus === 'UNCHECKED') && <JournalList />}
      </div>
    </div>
  );
};

export default Tutoring;
