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

  //TODO: 과외 상태에 대한 정보가 body에 들어가지 않아도 변경이 잘 되는지 확인 필요
  const patchTitle = async (value) => {
    await axios
      .patch(`tutoring/details/${tutoringId}`, {
        tutoringTitle: value,
      })
      .then(({ data }) => {
        setTutoring(data.data);
      });
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

  //TODO: GEP 특정 과외 후기 조회 API 수정되면 tutoringId로 바꾸기
  const getReviewDetail = async () => {
    await axios
      .get(`/reviews/11`)
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
