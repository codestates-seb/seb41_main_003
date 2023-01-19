import PropType from 'prop-types';
import styles from './Pagination.module.css';

//Props::
//pageInfo는 get으로 받은 데이터가 담고 있는 pageInfo를 담은 상태값을 말한다.
//buttonHandler는 숫자 버튼을 눌렀을 때 pageInfo 속의 page를 클릭한 버튼의 name 값으로 변경하는 함수
//이 때 변경하는 page 값은 pageInfo와 별개의 상태값으로 설정되어 있어야 무한 루프 돌지 않음.
//TutorContents 컴포넌트 참고 가능

const Pagination = ({ pageInfo, buttonHandler }) => {
  const { page, totalPages } = pageInfo;
  const pageArray = new Array(totalPages).fill(0).map((_, idx) => idx + 1);

  return (
    <div className={styles.pagenation}>
      {pageArray.map((el, idx) => (
        <button
          className={el === page + 1 && styles.active}
          name={idx}
          key={el}
          onClick={buttonHandler}
        >
          {el}
        </button>
      ))}
    </div>
  );
};

Pagination.propTypes = {
  buttonHandler: PropType.func,
  pageInfo: PropType.object,
};

export default Pagination;
