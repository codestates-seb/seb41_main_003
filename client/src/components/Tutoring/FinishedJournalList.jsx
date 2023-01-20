import styles from './FinishedJournalList.module.css';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { Link, useParams } from 'react-router-dom';
import Profile from '../../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import PropType from 'prop-types';

const FinishedJournalList = ({ tutoring, pageInfo, setTutoring }) => {
  //TODO:무한 스크롤 구현
  const [reviewDetail, setReviewDetail] = useState({
    reviewId: 0,
    professional: 0,
    readiness: 0,
    explanation: 0,
    punctuality: 0,
    reviewBody: '',
    tuteeName: '',
    createAt: '',
    updateAt: '',
  });
  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);
  const { userStatus } = useRecoilValue(Profile);
  const { tutoringId } = useParams();

  const confirmTextProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: '과외 제목을 아래와 같이 수정합니다.',
      modalHandler: (e, value) => {
        console.log(value);
        reset();
        patchTitle(value);
      },
      placeHolder: '새로운 과외 제목 입력',
    },
  };

  const patchTitle = async (value) => {
    await axios
      .patch(`tutoring/details/${tutoringId}`, { tutoringTitle: value })
      .then(({ data }) => {
        setTutoring(data.data);
      });
  };

  const confirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `과외를 삭제 하시겠습니까?
      과외 삭제 시 과외 관리 내역이 모두 삭제됩니다.`,
      modalHandler: () => {
        console.log('과외 삭제 요청이 갑니다~');
        reset();
        deleteTutoring();
      },
    },
  };

  const deleteTutoring = async () => {
    await axios
      .delete(`tutoring/details/${tutoringId}`)
      .then(() => console.log('과외가 삭제되어부림'))
      .catch((err) => console.log(err));
  };

  const reviewDetailProps = {
    isOpen: true,
    modalType: 'reviewDetail',
    props: {
      modalHandler: (e) => {
        if (e.target.name === 'edit') {
          console.log('리뷰 수정 하러 갑시다!');
          setModal(editReviewProps);
        }
      },
      reviewDetail: reviewDetail,
    },
  };

  const getReviewDetail = async () => {
    await axios
      .get(`/reviews/${tutoringId}`)
      .then(({ data }) => setReviewDetail(data.data))
      .catch((err) => console.log(err));
  };

  const editReviewProps = {
    isOpen: true,
    modalType: 'editReview',
    props: {
      modalHandler: (e, value, reviewData) => {
        patchReview(reviewData, value);
        console.log('후기 수정 요청 할 겁니다!');
        setModal(alertProps);
      },
      initialReview: reviewDetail,
    },
  };

  const patchReview = async (review, comment) => {
    await axios
      .patch(`/reviews/${tutoringId}`, {
        ...review,
        reviewBody: comment,
      })
      .then(({ data }) => setReviewDetail({ ...data.data }));
  };

  const alertProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '리뷰가 수정되었습니다.',
    },
  };

  useEffect(() => {
    getReviewDetail();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.leftCard}>
        <Link to={`/journal/${tutoring.latestNoticeId}`}>
          <div className={styles.noti}>
            <HiSpeakerphone className={styles.icon} />
            {tutoring.latestNoticeBody > 20
              ? `최근 공지사항 | ${tutoring.latestNoticeBody.slice(0, 20)}...`
              : `최근 공지사항 | ${tutoring.latestNoticeBody}`}
          </div>
        </Link>
        <ul className={styles.list}>
          {tutoring.dateNotices.map((el) => (
            <Link to={`/journal/${el.dateNoticeId}`} key={el.dateNoticeId}>
              <li key={el.dateNoticeId} className={styles.li}>
                <div className={styles.dateBox}>
                  <span className={styles.day}>
                    {new Date(el.startTime).getDate()}
                  </span>
                  <span className={styles.yearMonth}>{`${new Date(
                    el.startTime
                  ).getFullYear()}년 ${
                    new Date(el.startTime).getMonth() + 1
                  }월`}</span>
                </div>
                <div className={styles.textBox}>
                  <span
                    className={styles.goal}
                  >{`학습 목표 | ${el.dateNoticeTitle} `}</span>
                  <span
                    className={styles.homework}
                  >{`과제 제출 완료 (${el.finishHomeworkCount}/${el.homeworkCount})`}</span>
                </div>
                <div className={styles.notiIcon}>
                  <HiSpeakerphone className={styles.hiSpeaker} />
                  공지
                </div>
              </li>
            </Link>
          ))}
        </ul>
      </div>
      <div className={styles.rightCard}>
        <div className={styles.rightTextBox}>
          <div className={styles.nameBox}>
            <span>
              {userStatus === 'TUTOR' ? tutoring.tuteeName : tutoring.tutorName}
            </span>
            <button
              onClick={(e) => {
                e.preventDefault();
                setModal(confirmTextProps);
              }}
            >
              <MdEdit className={styles.mdEdit} />
              <span>제목 수정하기</span>
            </button>
          </div>
          <span className={styles.tutoringTitle}>{tutoring.tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(tutoring.createAt).toLocaleDateString()} ~ ${new Date(
              tutoring.updateAt
            ).toLocaleDateString()}`}
          </span>
        </div>
        <div className={styles.buttonBox}>
          <ButtonNightBlue
            text="작성된 리뷰 확인"
            buttonHandler={(e) => {
              e.preventDefault();
              setModal(reviewDetailProps);
            }}
          />
          <ButtonRed
            text="과외 삭제"
            buttonHandler={(e) => {
              e.preventDefault();
              setModal(confirmProps);
            }}
          />
        </div>
      </div>
    </div>
  );
};

FinishedJournalList.propTypes = {
  tutoring: PropType.object,
  pageInfo: PropType.object,
  setTutoring: PropType.func,
};

export default FinishedJournalList;
