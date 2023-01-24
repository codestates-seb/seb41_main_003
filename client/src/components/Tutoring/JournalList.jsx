import styles from './JournalList.module.css';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { useNavigate, Link, useLocation } from 'react-router-dom';
import Profile from '../../recoil/profile';
import axios from 'axios';
import { useEffect } from 'react';
import PropType from 'prop-types';
import Journals from './Journals';

const JournalList = ({ tutoring, setTutoring, pageInfo, setPageInfo }) => {
  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);
  const navigate = useNavigate();
  const { userStatus } = useRecoilValue(Profile);
  const { tutoringId } = useLocation().state;

  const alertProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '상대방에게 과외 종료가 요청되었습니다 \n 상대방이 종료를 수락하면 과외가 종료됩니다',
    },
  };

  const confirmTextProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: '과외 제목을 아래와 같이 수정합니다.',
      modalHandler: (e, value) => {
        reset();
        patchTitle(value);
      },
      placeHolder: '새로운 과외 제목 입력',
    },
  };

  const patchTitle = async (value) => {
    await axios
      .patch(`tutoring/details/${tutoringId}`, {
        tutoringTitle: value,
        tutoringStatus: tutoring.tutoringStatus,
      })
      .then(({ data }) => {
        setTutoring(data.data);
      });
  };

  const confirmValiProps = {
    isOpen: true,
    modalType: 'confirmVali',
    props: {
      text: '과외 종료를 원하신다면 \n 아래의 입력창에 "과외 종료" 를 입력 후 \n 확인 버튼을 눌러주세요.\n \n (과외가 종료되면 과외 제목 수정이나 \n 일지 작성이 불가능해집니다.)',
      validation: '과외 종료',
      modalHandler: () => {
        patchWaitFinish();
        setModal(alertProps);
      },
    },
  };

  const patchWaitFinish = async () => {
    await axios
      .patch(`/tutoring/details/${tutoringId}`, {
        tutoringStatus: 'WAIT_FINISH',
      })
      .then(({ data }) => setTutoring({ ...data.data }))
      .catch((err) => console.log(err));
  };

  const finishRquestProps = {
    isOpen: true,
    modalType: 'bothHandler',
    backDropHandle: true,
    props: {
      text: '상대방이 과외 종료를 요청하였습니다. \n 과외 종료를 수락하시겠습니까? \n 과외가 종료되면 과외 제목 수정이 불가능해집니다.',
      modalHandler: (e) => {
        const { name } = e.target;
        if (name === 'yes') {
          setModal(reviewProps);
        } else if (name === 'no') {
          patchFinish();
          setModal(alertRejectProps);
        }
      },
    },
  };

  const patchFinish = async () => {
    await axios
      .patch(`/tutoring/details/${tutoringId}`, {
        tutoringStatus: 'PROGRESS',
      })
      .then(({ data }) => setTutoring({ ...data.data }))
      .catch((err) => console.log(err));
  };

  const alertRejectProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '과외 종료 요청이 거절되었습니다.',
    },
  };

  const reviewProps = {
    isOpen: true,
    backDropHandle: true,
    modalType: 'review',
    props: {
      modalHandler: (e, value, reviewData) => {
        postReview(reviewData, value);
        console.log('리뷰 작성을 완료했다!');
        setModal(alertFinishProps);
      },
    },
  };

  const alertFinishProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '과외가 종료되었습니다!',
    },
  };

  const postReview = async (review, comment) => {
    await axios
      .post(`reviews/${tutoringId}`, {
        ...review,
        reviewBody: comment,
      })
      .then((res) => console.log(res));
  };

  useEffect(() => {
    if (userStatus === 'TUTEE' && tutoring.tutoringStatus === 'WAIT_FINISH')
      setModal(finishRquestProps);
  });

  return (
    <div className={styles.container}>
      <div className={styles.leftCard}>
        {tutoring.latestNoticeBody &&
          (tutoring.latestNoticeBody ? (
            <Link
              className={styles.noti}
              to={`/journal`}
              state={{
                tutoringId: tutoring.tutoringId,
                dateNoticeId: tutoring.latestNoticeId,
              }}
            >
              <div>
                <HiSpeakerphone className={styles.icon} />
                {tutoring.latestNoticeBody > 20
                  ? `최근 공지사항 | ${tutoring.latestNoticeBody.slice(
                      0,
                      20
                    )}...`
                  : `최근 공지사항 | ${tutoring.latestNoticeBody}`}
              </div>
            </Link>
          ) : (
            <div className={styles.noti}>
              <div>
                <HiSpeakerphone className={styles.icon} />
                최근 공지가 없습니다.
              </div>
            </div>
          ))}
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
              {tutoring.tuteeName &&
                (userStatus === 'TUTOR'
                  ? tutoring.tuteeName
                  : tutoring.tutorName)}
            </span>
          </div>
          <span className={styles.tutoringTitle}>{tutoring.tutoringTitle}</span>
          <div className={styles.titleEditButtonBox}>
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
          <span className={styles.tutoringDate}>
            {`${new Date(tutoring.createAt).toLocaleDateString()} ~`}
          </span>
          {userStatus === 'TUTOR' &&
            tutoring.tutoringStatus === 'WAIT_FINISH' && (
              <span className={styles.waitingNoti}>
                {`상대방의 과외 종료 요청 수락을 기다리는 중입니다.`}
              </span>
            )}
        </div>
        {userStatus === 'TUTOR' && tutoring.tutoringStatus === 'PROGRESS' && (
          <div className={styles.buttonBox}>
            <ButtonNightBlue
              text="과외 일지 작성"
              buttonHandler={() =>
                navigate(`/addjournal`, {
                  state: { tutoringId },
                })
              }
            />
            <ButtonRed
              text="과외 종료"
              buttonHandler={(e) => {
                e.preventDefault();
                setModal(confirmValiProps);
              }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

JournalList.propTypes = {
  tutoring: PropType.object,
  setTutoring: PropType.func,
  pageInfo: PropType.object,
  setPageInfo: PropType.func,
};

export default JournalList;
