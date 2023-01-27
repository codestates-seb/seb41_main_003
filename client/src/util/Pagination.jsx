import PropType from 'prop-types';
import { MdKeyboardArrowLeft, MdKeyboardArrowRight } from 'react-icons/md';
import styles from './Pagination.module.css';

//Props::
//pageInfo는 get으로 받은 데이터가 담고 있는 pageInfo를 담은 상태값을 말한다.
//buttonHandler는 숫자 버튼을 눌렀을 때 pageInfo 속의 page를 클릭한 버튼의 name 값으로 변경하는 함수
//이 때 변경하는 page 값은 pageInfo와 별개의 상태값으로 설정되어 있어야 무한 루프 돌지 않음.
//TutorContents 컴포넌트 참고 가능

const Pagination = ({ pageInfo, setPage }) => {
  const { page, totalPages } = pageInfo;
  // const [currentPage, setCurrentPage] = useState(page + 1);

  const buttonHandler = ({ currentTarget: { name } }) => {
    if (name === 'prev' && page !== 0) {
      setPage(page - 1);
    }
    if (name === 'next' && page !== totalPages - 1) {
      setPage(page + 1);
    }
  };

  return (
    <div className={styles.pagenation}>
      <button name="prev" onClick={buttonHandler}>
        <MdKeyboardArrowLeft />
      </button>

      <button className={styles.active}>
        {page !== undefined && page + 1}
      </button>
      <button name="next" onClick={buttonHandler}>
        <MdKeyboardArrowRight />
      </button>
    </div>
  );
};

Pagination.propTypes = {
  setPage: PropType.func,
  pageInfo: PropType.object,
};

export default Pagination;
