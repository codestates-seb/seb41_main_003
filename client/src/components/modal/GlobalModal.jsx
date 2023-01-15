import styles from './GlobalModal.module.css';
import { useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal.js';
import {
  AlertModal,
  ConfirmModal,
  ConfirmTextModal,
  ConfirmValiModal,
} from './DefaultModal.jsx';
import ReviewModal from './ReviewModal';
import ReviewDetailModal from './ReviewDetail';
import EditReviewModal from './EditReviewModal';

export const GlobalModal = () => {
  const reset = useResetRecoilState(ModalState);
  const { isOpen, modalType, props } = useRecoilValue(ModalState);
  if (!isOpen) return;

  const modal = {
    alert: <AlertModal {...props} />,
    confirm: <ConfirmModal {...props} />,
    confirmVali: <ConfirmValiModal {...props} />,
    confirmText: <ConfirmTextModal {...props} />,
    review: <ReviewModal {...props} />,
    reviewDetail: <ReviewDetailModal {...props} />,
    editReview: <EditReviewModal {...props} />,
  };

  return (
    <div className={styles.backdrop} onClick={() => reset()} aria-hidden="true">
      {modal[modalType]}
    </div>
  );
};
