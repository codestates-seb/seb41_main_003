import styles from './TutoringList.module.css';
import PropType from 'prop-types';
import axios from 'axios';
import Tutoring from '../components/TutoringList/Tutoring';
import { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import Profile from '../recoil/profile';
import Pagination from '../util/Pagination';

const initialState = {
  data: [
    {
      tutoringId: 1,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수1231241241학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 2,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 3,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'FINISH',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 4,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 5,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 6,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 7,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 8,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 9,
      tutorName: '튜터이름',
      tuteeName: '유영민',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
  ],
  pageInfo: {
    page: 0,
    size: 9,
    totalElements: 9,
    totalPages: 2,
  },
};

const TutoringList = () => {
  const [tutorings, setTutorings] = useState(initialState);
  const [pageInfo, setPageInfo] = useState(initialState.pageInfo);
  const [isFinished, setIsFinished] = useState(false);
  const { profilId } = useRecoilValue(Profile);
  const [page, setPage] = useState(0);

  const filterHandler = (e) => {
    const { name } = e.target;
    if (name === 'current') setIsFinished(false);
    else setIsFinished(true);
  };
  const aa = localStorage.getItem('current_user');
  console.log(isFinished, 'isFinished');
  console.log(profilId, 'profilId');
  console.log(aa.profilId, '?');

  //progress/finish 상태를 받아오는 API를 따로 만들고
  //진행중인과외/종료된 과외 누르면 불러오기
  // const getTutoringList = async () => {
  //   await axios
  //     .get(`/tutoring/${profilId}?get=${isFinished ? 'FINISH' : 'PROGRESS'}?page=${page}`)
  //     .then((res) => {
  //       console.log(res.data.data);
  //       setTutorings(res.data.data);
  //       setPageInfo(res.data.pageInfo)
  //     })
  //     .catch((error) => console.log(error));
  // };
  // useEffect(() => {
  //   getTutoringList();
  // }, [page]);
  // useEffect(() => {
  //   getTutoringList();
  // }, [isFinished]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>과외 리스트</h2>
        <div className={styles.filter}>
          <button
            name="current"
            onClick={filterHandler}
            className={isFinished ? '' : `${styles.active}`}
          >
            진행 중인 과외
          </button>
          <span>&nbsp;|&nbsp;</span>
          <button
            name="finished"
            onClick={filterHandler}
            className={isFinished ? `${styles.active}` : ''}
          >
            종료된 과외
          </button>
        </div>
        <ul className={styles.tutoringList}>
          {tutorings &&
            tutorings.data.map((tutoring) => (
              <Tutoring tutoring={tutoring} key={tutoring.tutoringId} />
            ))}
        </ul>
        <Pagination
          pageInfo={pageInfo}
          buttonHandler={(e) => {
            const { name } = e.target;
            setPage(name);
          }}
        />
      </div>
    </div>
  );
};

export default TutoringList;
