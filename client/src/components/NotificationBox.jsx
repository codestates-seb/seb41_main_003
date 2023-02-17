import styles from './NotificationBox.module.css';
import NoticeItem from './NoticeItem';
import { Link } from 'react-router-dom';
import { MdCheck, MdClose } from 'react-icons/md';

const dummy = {
  data: [
    {
      noticeId: 1,
      noticeStatus: 'UNCHECK',
      contentType: 'MESSAGE',
      profileName: '빨간점은 필수1111111',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 2,
      noticeStatus: 'UNCHECK',
      contentType: 'TUTORING_REQUEST',
      profileName: '빨간점은 필수22',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 3,
      noticeStatus: 'CHECK',
      contentType: 'TUTORING_MATCH',
      profileName: '빨간점은 필수33',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 4,
      noticeStatus: 'CHECK',
      contentType: 'DATE_NOTICE',
      profileName: '빨간점은44',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 5,
      noticeStatus: 'CHECK',
      contentType: 'WAIT_FINISH',
      profileName: '빨간점은 필수55',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 6,
      noticeStatus: 'CHECK',
      contentType: 'FINISH',
      profileName: '빨간점은 필수66',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 6,
      noticeStatus: 'CHECK',
      contentType: 'FINISH',
      profileName: '빨간점은 필수66',
      createAt: '2023-01-16T10:32:23.634168',
    },

    {
      noticeId: 6,
      noticeStatus: 'CHECK',
      contentType: 'FINISH',
      profileName: '빨간점은 필수66',
      createAt: '2023-01-16T10:32:23.634168',
    },
    {
      noticeId: 6,
      noticeStatus: 'CHECK',
      contentType: 'FINISH',
      profileName: '빨간점은 필수66',
      createAt: '2023-01-16T10:32:23.634168',
    },
  ],
  pageInfo: {
    page: 1,
    size: 1,
    totalElements: 1,
    totalPages: 1,
  },
};
const NotificationBox = () => {
  const noticeList = dummy.data;

  return (
    <div className={styles.noticeBoxContainer}>
      <ul className={styles.noticeListArea}>
        {noticeList?.map((notice) => (
          <Link key={notice.noticeId}>
            <NoticeItem data={notice} />
          </Link>
        ))}
      </ul>
      <div className={styles.buttonArea}>
        <button>
          <MdCheck size={20} />
          <span>전체 확인</span>
        </button>
        <button>
          <MdClose size={20} />
          <span>전체 삭제</span>
        </button>
      </div>
    </div>
  );
};

export default NotificationBox;
