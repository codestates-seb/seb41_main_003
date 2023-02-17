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
        <div>
          <MdChatBubble size={16} pull="right" />
          <span>{profileName}님이 보낸 메세지가 도착했습니다.</span>
        </div>
      ) : contentType === 'TUTORING_REQUEST' ? (
        <div>
          <BsPersonFill size={16} />
          <span>{profileName}님이 매칭을 요청했습니다.</span>
        </div>
      ) : contentType === 'TUTORING_MATCH' ? (
        <div>
          <BsPersonCheckFill size={16} />
          <span>{profileName}님이 매칭을 수락했습니다.</span>
        </div>
      ) : contentType === 'DATE_NOTICE' ? (
        <div>
          <BiCalendar size={16} />
          <span>{profileName}님이 새로운 일지를 작성했습니다.</span>
        </div>
      ) : contentType === 'WAIT_FINISH' ? (
        <div>
          <BiCalendarExclamation size={16} />
          <span>{profileName}님이 과외 종료를 요청했습니다.</span>
        </div>
      ) : contentType === 'FINISH' ? (
        <div>
          <BiCalendarCheck size={16} />
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
