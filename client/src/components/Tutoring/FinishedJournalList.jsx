import styles from './FinishedJournalList.module.css';
import dummyTutoringData from './dummyTutoringData';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import { Link } from 'react-router-dom';

const FinishedJournalList = () => {
  //TODO:현재 이 컴포넌트는 튜티 기준으로 만들어짐 이후 튜터, 튜티 분기해 수정 필요
  //name부분과 과외 일지 작성 버튼 부분이 튜터와 튜티가 달라야 함
  // 작성된 리뷰 확인 버튼은 튜티인 경우에만 존재함

  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);

  const confirmTextProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: '과외 제목을 아래와 같이 수정합니다.',
      modalHandler: (e, value) => {
        console.log(value);
        reset();
        //TODO : 과외 제목 수정 관련 API 요청
      },
      placeHolder: '새로운 과외 제목 입력',
    },
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
        //TODO : 과외 삭제 요청
      },
    },
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
    },
  };

  const editReviewProps = {
    isOpen: true,
    modalType: 'editReview',
    props: {
      modalHandler: (e, value, reviewData) => {
        //TODO: 후기 수정 요청 보내기
        console.log('후기 수정 요청 할 겁니다!');
        setModal(alertProps);
      },
    },
  };

  const alertProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '리뷰가 수정되었습니다.',
    },
  };

  const {
    tutoringTitle,
    tuteeName,
    tutorName,
    dateNotices,
    updateAt,
    createAt,
    latestNotice,
    latestNoticeId,
  } = dummyTutoringData;

  return (
    <div className={styles.container}>
      <div className={styles.leftCard}>
        {/* TODO: Link 컴포넌트를 통해 들어간 일지 페이지에서는 /tutoring/date-notice/{latestNoticeId} 와 같은 파라미터 추가해서 특정 날짜 일지 API 요청 */}
        <Link to="/journal">
          <div className={styles.noti}>
            <HiSpeakerphone className={styles.icon} />
            {latestNotice > 20
              ? `최근 공지사항 | ${latestNotice.slice(0, 20)}...`
              : `최근 공지사항 | ${latestNotice}`}
          </div>
        </Link>
        <ul className={styles.list}>
          {dateNotices.map((el) => (
            // TODO: 해당 일지 id에 따른 조회 필요
            <Link to="/journal" key={el.dateNoticeId}>
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
                {/* TODO: 최근 공지를 눌렀을 때 해당 공지가 있는 일지 상세 페이지로 이동해야 함  */}
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
            {/* TODO: user status에 따라 tutorName이 뜨거나 tuteeName이 떠야 함  */}
            <span>{tutorName}</span>
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
          <span className={styles.tutoringTitle}>{tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(createAt).toLocaleDateString()} ~ ${new Date(
              updateAt
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

export default FinishedJournalList;
