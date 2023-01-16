import styles from './MessageContent.module.css';
import { TextInput } from '../Input';
import { MdMenu } from 'react-icons/md';
import PropType from 'prop-types';
import { useState } from 'react';
import {
  AlertModal,
  ConfirmModal,
  ConfirmTextModal,
} from '../modal/DefaultModal';
import Chat from './Chat';

const MessageContent = ({ messages }) => {
  const [isMenu, setIsMenu] = useState(false);
  const [inputValue, setInputValue] = useState('');

  // * Modal 창 관련 상태, 핸들러
  // TODO : 모달 컴포넌트 분기 및 상태 정리 필요
  const [isMatchConfirm, setIsMatchConfirm] = useState({
    content: `상대방의 요청 수락 시 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,
    value: '',
    isOpen: false,
  });
  const [isCancelConfirm, setIsCancelConfirm] = useState({
    content: `상담 취소 하시겠습니까?
      상담 취소 시 대화 내역이 모두 삭제됩니다.`,
    isOpen: false,
  });
  const [isMatchAlert, setIsMatchAlert] = useState({
    content: `매칭 요청이 완료되었습니다.
    상대방의 요청 수락 이후에는 과외 관리 페이지에서
    확인하실 수 있습니다.`,
    isOpen: false,
  });
  const [isCancelAlert, setIsCancelAlert] = useState({
    content: `상담이 취소되었습니다.`,
    isOpen: false,
  });

  const cancelHandler = (e) => {
    const { name } = e.target;
    setIsCancelConfirm({
      ...isCancelConfirm,
      isOpen: !isCancelConfirm.isOpen,
    });
    if (name === 'yes') {
      // TODO : 상담 취소 관련 API 연결 필요
      setIsCancelAlert({ ...isCancelAlert, isOpen: !isCancelAlert.isOpen });
    }
  };

  const matchHandler = (e) => {
    const { name } = e.target;
    setIsMatchConfirm({
      ...isMatchConfirm,
      isOpen: !isMatchConfirm.isOpen,
      value: '',
    });
    if (name === 'yes') {
      // TODO : 매칭 신청 관련 API 필요
      console.log(isMatchConfirm.value);
      setIsMatchAlert({ ...isMatchAlert, isOpen: !isMatchAlert.isOpen });
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.messageContainer}>
        {messages.map((message) => (
          <Chat message={message} authId={1} key={message.messageId} />
        ))}
      </div>
      <div className={styles.sendContainer}>
        <button
          className={styles.menu}
          onClick={() => setIsMenu(true)}
          onBlur={() => {
            setTimeout(() => setIsMenu(false), 100);
          }}
        >
          <MdMenu />
        </button>
        <TextInput
          id="sendMsg"
          placeHolder="메세지를 입력하세요"
          type="text"
          value={inputValue}
          handler={(e) => setInputValue(e.target.value)}
        />
        <button className={styles.sendBtn}>전송</button>

        {/* dropDown */}
        {isMenu && (
          <div className={styles.dropdown}>
            <button
              onClick={() => {
                setIsMatchConfirm({
                  ...isMatchConfirm,
                  isOpen: !isMatchConfirm.isOpen,
                });
              }}
            >
              매칭 요청
            </button>
            <button
              onClick={() => {
                setIsCancelConfirm({
                  ...isCancelConfirm,
                  isOpen: !isCancelConfirm.isOpen,
                });
              }}
            >
              상담 취소
            </button>
          </div>
        )}
      </div>

      {/* // * 모달 */}
      {/* // TODO : 모달 컴포넌트 분기 및 상태 정리 필요 */}
      {isMatchConfirm.isOpen && (
        <ConfirmTextModal
          text={isMatchConfirm.content}
          modalHandler={matchHandler}
          value={isMatchConfirm.value}
          valueHandler={(e) => {
            setIsMatchConfirm({ ...isMatchConfirm, value: e.target.value });
          }}
          placeHolder="과외의 이름을 작성해주세요"
        />
      )}
      {isCancelConfirm.isOpen && (
        <ConfirmModal
          text={isCancelConfirm.content}
          modalHandler={cancelHandler}
        />
      )}
      {isMatchAlert.isOpen && (
        <AlertModal
          text={isMatchAlert.content}
          modalHandler={() =>
            setIsMatchAlert({ ...isMatchAlert, isOpen: !isMatchAlert.isOpen })
          }
        />
      )}
      {isCancelAlert.isOpen && (
        <AlertModal
          text={isCancelAlert.content}
          modalHandler={() =>
            setIsCancelAlert({
              ...isCancelAlert,
              isOpen: !isCancelAlert.isOpen,
            })
          }
        />
      )}
    </div>
  );
};

MessageContent.propTypes = {
  messages: PropType.array,
};

export default MessageContent;
