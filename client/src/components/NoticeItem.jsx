import styles from './NoticeItem.module.css';
import PropTypes from 'prop-types';
import { MdCheck, MdClose, MdChatBubble } from 'react-icons/md';
import { BsPersonFill, BsPersonCheckFill } from 'react-icons/bs';
import {
  BiCalendar,
  BiCalendarExclamation,
  BiCalendarCheck,
} from 'react-icons/bi';

const NoticeItem = ({ data }) => {
  const { noticeStatus, contentType, profileName } = data;

  return (
    <li
      className={`${styles.noticeContainer} ${
        noticeStatus === 'CHECK' ? styles.checked : undefined
      }`}
    >
      {contentType === 'MESSAGE' ? (
        <div className={styles.content}>
          <MdChatBubble pull="right" />
          <span>{profileName}님이 보낸 메세지가 도착했습니다.</span>
        </div>
      ) : contentType === 'TUTORING_REQUEST' ? (
        <div className={styles.content}>
          <BsPersonFill />
          <span>{profileName}님이 매칭을 요청했습니다.</span>
        </div>
      ) : contentType === 'TUTORING_MATCH' ? (
        <div className={styles.content}>
          <BsPersonCheckFill />
          <span>{profileName}님이 매칭을 수락했습니다.</span>
        </div>
      ) : contentType === 'DATE_NOTICE' ? (
        <div className={styles.content}>
          <BiCalendar />
          <span>{profileName}님이 새로운 일지를 작성했습니다.</span>
        </div>
      ) : contentType === 'WAIT_FINISH' ? (
        <div className={styles.content}>
          <BiCalendarExclamation />
          <span>{profileName}님이 과외 종료를 요청했습니다.</span>
        </div>
      ) : contentType === 'FINISH' ? (
        <div className={styles.content}>
          <BiCalendarCheck />
          <span>{profileName}님이 과외 종료를 수락했습니다.</span>
        </div>
      ) : (
        <></>
      )}
      <div className={styles.buttonArea}>
        {noticeStatus === 'UNCHECK' ? (
          <button>
            <MdCheck size={20} />
            <span>확인</span>
          </button>
        ) : (
          <></>
        )}
        <button>
          <MdClose size={20} />
          <span>삭제</span>
        </button>
      </div>
    </li>
  );
};

NoticeItem.propTypes = {
  data: PropTypes.object,
};

export default NoticeItem;
