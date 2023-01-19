import styles from './MessageList.module.css';
import defaultUser from '../../assets/defaultUser.png';
import PropTypes from 'prop-types';
import { useSetRecoilState } from 'recoil';
import CurrentRoomIdState from '../../recoil/currentRoomId.js';

const MessageList = ({ messageList }) => {
  const setCurrentRoomId = useSetRecoilState(CurrentRoomIdState);

  const getCurrentRoomId = (e) => {
    setCurrentRoomId(e.currentTarget.id);
    console.log(getCurrentRoomId, 'getCurrentRoomId');
  };

  return (
    <div>
      <ul className={styles.messageList}>
        {messageList.map((obj) => {
          return (
            <li key={obj.messageRoomId} className={styles.message}>
              <button
                id={obj.messageRoomId}
                onClick={getCurrentRoomId}
                className={styles.person}
              >
                <img src={defaultUser} alt="user" className={styles.userImg} />
                <div>
                  <h4>{obj.targetName}</h4>
                  <p>{obj.lastMessage}</p>
                </div>
                {obj.messageStatus === 'UNCHECK' && (
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
  getMessageRoom: PropTypes.func,
  setCurrentRoomId: PropTypes.func,
};

export default MessageList;
