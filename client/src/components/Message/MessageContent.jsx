import styles from './MessageContent.module.css';
import { TextInput } from '../Input';
import { MdMenu } from 'react-icons/md';
import PropType from 'prop-types';
import { useState } from 'react';
import Chat from './Chat';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

const MessageContent = ({ messages }) => {
  const [isMenu, setIsMenu] = useState(false);
  const [inputValue, setInputValue] = useState('');

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  // * Modal 창 관련 props
  const matchConfirmProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: `상대방의 요청 수락 시 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,
      modalHandler: (_, value) => {
        // TODO : 매칭 신청 관련 API 필요
        console.log(value);
        resetModal();
        setModal(matchAlertProps);
      },
      placeHolder: '과외의 이름을 작성하세요',
    },
  };

  const cancelConfirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `상담 취소 하시겠습니까?
      상담 취소 시 대화 내역이 모두 삭제됩니다.`,
      modalHandler: () => {
        // TODO : 상담 취소 관련 API 필요
        console.log('상담 취소');
        resetModal();
        setModal(cancelAlertProps);
      },
    },
  };

  const matchAlertProps = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: `매칭 요청이 완료되었습니다.
    상대방의 요청 수락 이후에는 과외 관리 페이지에서
    확인하실 수 있습니다.`,
    },
  };

  const cancelAlertProps = {
    isOpen: true,
    modalType: 'alert',
    props: { text: `상담이 취소되었습니다.` },
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
            <button onClick={() => setModal(matchConfirmProps)}>
              매칭 요청
            </button>
            <button onClick={() => setModal(cancelConfirmProps)}>
              상담 취소
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

MessageContent.propTypes = {
  messages: PropType.array,
};

export default MessageContent;
