import styles from './Tutoring.module.css';
import JournalList from '../components/Tutoring/JournalList';
import FinishedJournalList from '../components/Tutoring/FinishedJournalList';
import { useParams } from 'react-router-dom';
import Profile from '../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

//TODO: 과외 관리 페이지는 하나이지만 종료된 과외 관리 & 진행중인 과외 관리 컴포넌트가 각각 만들어져 있음
//종료된 과외를 관리하러 들어오느냐, 진행중인 과외를 관리하러 들어오느냐에 따라 분기해서 컴포넌트를 보여주어야 함 (현재는 더미 데이터 이용해 분기함)
//tutoringStatus를 통해서 진행중인 과외와 종료된 과외를 구분할 수 있음 (진행 중 : UNCHECKED & PROGRESS, 종료 : FINISHED )
//사용자가 튜터이냐 튜티이냐에 대한 분기는 (아마도) 로컬 스토리지에 저장될 user Status를 이용해 컴포넌트 안에서 합니다.

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
      .get(`tutoring/details/${profileId}/${tutoringId}?size=6`)
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
