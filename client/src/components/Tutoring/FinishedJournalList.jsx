import styles from './FinishedJournalList.module.css';
import { HiSpeakerphone } from 'react-icons/hi';
import { ButtonNightBlue } from '../Button';
import { useSetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { Link, useLocation } from 'react-router-dom';
import Profile from '../../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import PropType from 'prop-types';
import Journals from './Journals';

const FinishedJournalList = ({
  tutoring,
  pageInfo,
  setPageInfo,
  setTutoring,
}) => {
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
  const { userStatus } = useRecoilValue(Profile);
  const { tutoringId } = useLocation().state;

  const reviewDetailProps = {
    isOpen: true,
    modalType: 'reviewDetail',
    props: {
      modalHandler: (e) => {
        if (e.target.name === 'edit') {
          setModal(editReviewProps);
        }
      },
      reviewDetail: reviewDetail,
    },
  };

  const getReviewDetail = async () => {
    await axios
      .get(`/reviews/${tutoring.reviewId}`)
      .then(({ data }) => setReviewDetail(data.data))
      .catch((err) => console.log(err));
  };

  const editReviewProps = {
    isOpen: true,
    modalType: 'editReview',
    props: {
      modalHandler: (e, value, reviewData) => {
        patchReview(reviewData, value);
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
        <Link to={`/journal`} state={{ dateNoticeId: tutoring.latestNoticeId }}>
          <div className={styles.noti}>
            <HiSpeakerphone className={styles.icon} />
            {tutoring.latestNoticeBody > 20
              ? `최근 공지사항 | ${tutoring.latestNoticeBody.slice(0, 20)}...`
              : `최근 공지사항 | ${tutoring.latestNoticeBody}`}
          </div>
        </Link>
        <Journals
          tutoringId={tutoringId}
          tutoring={tutoring}
          setTutoring={setTutoring}
          pageInfo={pageInfo}
          setPageInfo={setPageInfo}
        />
      </div>
      <div className={styles.rightCard}>
        <div className={styles.rightTextBox}>
          <div className={styles.nameBox}>
            <span>
              {userStatus === 'TUTOR' ? tutoring.tuteeName : tutoring.tutorName}
            </span>
          </div>
          <span className={styles.tutoringTitle}>{tutoring.tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(tutoring.createAt).toLocaleDateString()} ~ ${new Date(
              tutoring.updateAt
            ).toLocaleDateString()}`}
          </span>
        </div>
        {userStatus === 'TUTEE' && (
          <div className={styles.buttonBox}>
            <ButtonNightBlue
              text="작성된 리뷰 확인"
              buttonHandler={(e) => {
                e.preventDefault();
                setModal(reviewDetailProps);
              }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

FinishedJournalList.propTypes = {
  tutoring: PropType.object,
  pageInfo: PropType.object,
  setPageInfo: PropType.func,
  setTutoring: PropType.func,
};

export default FinishedJournalList;
