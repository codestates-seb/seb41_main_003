import { useState, useEffect } from 'react';
import Tutoring from '../components/TutoringList/Tutoring';
import styles from './TutoringList.module.css';
import PropType from 'prop-types';
import axios from 'axios';
import Profile from '../recoil/profile';
import { useRecoilValue } from 'recoil';

const initialState = {
  data: [
    {
      tutoringId: 1,
      tutorName: '강2호수',
      tuteeName: '김다은',
      tutoringTitle: '수1231241241학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 2,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 3,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 1,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 2,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 3,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 4,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 5,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'PROGRESS',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 6,
      tutorName: '강호수',
      tuteeName: '김다은',
      tutoringTitle: '수학 뿌셔 과학 뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
    {
      tutoringId: 9,
      tutorName: '강호수',
      tuteeName: '김다은11',
      tutoringTitle: '뿌셔뿌셔',
      tutoringStatus: 'UNCHECK',
      createAt: '2023-01-11T20:25:40.9355865',
      updateAt: '2023-01-11T20:25:40.9355865',
    },
  ],
  pageInfo: {
    page: 1,
    size: 10,
    totalElements: 10,
    totalPages: 2,
  },
};

const TutoringList = () => {
  const [tutorings, setTutorings] = useState(initialState);
  const [isFinished, setIsFinished] = useState(false);
  const { profilId } = useRecoilValue(Profile);

  const filterHandler = (e) => {
    const { name } = e.target;
    if (name === 'current') setIsFinished(false);
    else setIsFinished(true);
  };
  //progress/finish 상태를 받아오는 API를 따로 만고
  //진행중인과외/종료된 과외 누르면 불러오기
  const getTutoringList = async () => {
    await axios
      .get(`/tutoring/${profilId}?get=${isFinished ? 'FINISH' : 'PROGRESS'}`)
      .then((res) => {
        console.log(res.data.data);
        setTutorings(res.data.data);
      })
      .catch((error) => console.log(error));
  };
  useEffect(() => {
    getTutoringList();
  }, []);

  //TODO: util 폴더에 있는 Pagination 컴포넌트로 바꿔서 사용하기
  //TutoringList 속 pagination className도 제거하면 됨
  const Pagination = ({ pageInfo }) => {
    const { page, totalPages } = pageInfo;
    const pageArray = new Array(totalPages).fill(0).map((_, idx) => idx + 1);

    return (
      <div className={styles.pagination}>
        {pageArray.map((el) => (
          <button className={el === page && styles.active} key={el}>
            {el}
          </button>
        ))}
      </div>
    );
  };

  useEffect(() => {
    //TODO: isFinished 상태 변경에 따라서
    //tutorings 상태를 변경시킨다
    //특정 프로필 과외 리스트를 조회하는 API 요청을 보내면 되는데
    //tutoring/{profileId}?get=FINISH 와 같은 엔드 포인트로 요청을 보내면 됨
    getTutoringList();
  }, [isFinished]);

  Pagination.propTypes = {
    pageInfo: PropType.object,
  };

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
        <Pagination pageInfo={tutorings.pageInfo} />
      </div>
    </div>
  );
};

export default TutoringList;
