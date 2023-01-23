import styles from './MessageList.module.css';
import defaultUser from '../../assets/defaultUser.png';
import PropTypes from 'prop-types';
import { useSetRecoilState } from 'recoil';
import CurrentRoomIdState from '../../recoil/currentRoomId.js';

const MessageList = ({ messageList }) => {
  const setCurrentRoomId = useSetRecoilState(CurrentRoomIdState);

  const getCurrentRoomId = (e) => {
    setCurrentRoomId(e.currentTarget.id);
  };

  return (
    <div>
      <ul className={styles.messageList}>
        {messageList.map((list) => {
          return (
            <li key={list.messageRoomId} className={styles.message}>
              <button
                id={list.messageRoomId}
                onClick={getCurrentRoomId}
                className={styles.person}
              >
                <img
                  src={list.targetProfileUrl || defaultUser}
                  alt="user"
                  className={styles.userImg}
                />
                <div>
                  <h4>{list.targetName}</h4>
                  <p>{list.lastMessage}</p>
                </div>
                {list.messageStatus === 'UNCHECK' && (
                  <span className={styles.badge} />
                )}
              </button>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

MessageList.propTypes = {
  messageList: PropTypes.array,
};

export default MessageList;
