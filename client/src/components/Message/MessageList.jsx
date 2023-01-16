import styles from './MessageList.module.css';
import defaultUser from '../../assets/defaultUser.png';

const MessageListData = [
  {
    messageRoomId: 2,
    messageStatus: 'UNCHECK',
    lastMessage: '안녕하세요 튜티에요~',
    targetName: '김과학',
    createAt: '2023-01-16T10:11:52.102063',
  },
  {
    messageRoomId: 1,
    messageStatus: 'UNCHECK',
    lastMessage: 'REQ_UEST',
    targetName: '33번회원이다',
    createAt: '2023-01-16T10:05:39.041577',
  },
];

const MessageList = () => {
  return (
    <>
      <ul className={styles.messageList}>
        {MessageListData.map((el) => {
          return (
            <li key={el.messageRoomId} className={styles.message}>
              <button className={styles.person}>
                <img src={defaultUser} alt="user" className={styles.userImg} />
                <div>
                  <h4>{el.targetName}</h4>
                  <p>{el.lastMessage}</p>
                </div>
                {el.messageStatus === 'UNCHECK' && (
                  <span className={styles.badge} />
                )}
              </button>
            </li>
          );
        })}
      </ul>
    </>
  );
};

export default MessageList;
