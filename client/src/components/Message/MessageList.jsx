import styles from './MessageList.module.css';
import defaultUser from '../../assets/defaultUser.png';

const MessageList = () => {
  return (
    <>
      <ul className={styles.messageList}>
        {/* // TODO : API 나오면 배열 순회해서 출력하도록 수정해야함 */}
        <li>
          <button className={styles.person}>
            <img src={defaultUser} alt="user" className={styles.userImg} />
            <div>
              <h4>김민경</h4>
              <p>and i also 까 치 조 아</p>
            </div>
            <span className={styles.badge} />
          </button>
        </li>
      </ul>
    </>
  );
};

export default MessageList;
