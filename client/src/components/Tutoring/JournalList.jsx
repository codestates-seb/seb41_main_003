import styles from './JournalList.module.css';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { useNavigate, Link, useParams } from 'react-router-dom';
import Profile from '../../recoil/profile';
import axios from 'axios';
import { useState, useEffect } from 'react';
import PropType from 'prop-types';

const JournalList = ({ tutoring, setTutoring, pageInfo }) => {
  //TODO: GET 요청시 받은 pageInfo는 무한 스크롤 구현에 사용합니다
  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);
  const navigate = useNavigate();
  const { userStatus } = useRecoilValue(Profile);
  const { tutoringId } = useParams();

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

  const confirmValiProps = {
    isOpen: true,
    modalType: 'confirmVali',
    props: {
      text: '과외 종료를 원하신다면 \n 아래의 입력창에 "과외 종료" 를 입력 후 \n 확인 버튼을 눌러주세요.',
      validation: '과외 종료',
      modalHandler: () => {
        setModal(reviewProps);
      },
    },
  };

  const reviewProps = {
    isOpen: true,
    modalType: 'review',
    props: {
      modalHandler: (e, value, reviewData) => {
        postReview(reviewData, value);
        console.log('리뷰 작성을 완료했다!');
        setModal(alertProps);
        //TODO: 과외 종료 API 필요 tutoringStatus를 변경하는 등... 조치 필요
      },
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
              <li className={styles.li}>
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
            {`${new Date(tutoring.createAt).toLocaleDateString()} ~`}
          </span>
        </div>
        {userStatus === 'TUTOR' && (
          <div className={styles.buttonBox}>
            <ButtonNightBlue
              text="과외 일지 작성"
              buttonHandler={() => navigate(`/addjournal/${tutoringId}`)}
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
  pageInfo: PropType.object,
  setTutoring: PropType.func,
};

export default JournalList;
