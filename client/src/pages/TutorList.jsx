import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TutorList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import { useState, useEffect } from 'react';
import FilterDropdown from '../components/MainSection/FilterDropdown';
import { Link } from 'react-router-dom';
import { ButtonTop } from '../components/Button';
import MenuButtons from '../components/MainSection/FilterButton';
import axios from 'axios';

//TODO: 튜터 리스트를 불러오는 GET 요청 필요
//정렬 필터 파라미터 : sort = rate (기본값이 createAt 이므로 별점 순으로 변경시에만 파라미터 붙여서 요청하면 됨)
//과목 버튼 필터 파라미터 : subjectMenu 상태에서 꺼내와서 subject = 수학,영어,과학 같은 형식으로 요청
//검색창에서 검색 시 name = 강호수 와 같이 요청함

//TODO: Enter 쳤을 때만 검색어 검색이 되도록 수정 필요 (Enter 핸들러 작성)

const TutorList = () => {
  // API에서 받아온 데이터
  const [tutorData, setTutorData] = useState([]);
  const [pageInfo, setPageInfo] = useState({});
  //정렬 메뉴 오픈 상태
  const [isOpen, setIsOpen] = useState(false);
  //과목 필터 메뉴에서 선택한 과목들
  const [subjectMenu, setSubjectMenu] = useState([]);
  //검색창에서 입력한 검색어 반영
  const [search, setSearch] = useState('');
  //검색창 값 핸들링 상태
  const [searchValue, setSearchValue] = useState('');
  //정렬 메뉴 '최신 순'인지 '별점 순'인지
  const [sort, setSort] = useState('');

  const getTutorData = async () => {
    await axios
      .get(
        process.env.REACT_APP_BASE_URL +
          `/users/tutors?subject=${subjectMenu.join()}&name=${search}&sort=${sort}`
      )
      .then(({ data }) => {
        setTutorData(data.data);
        setPageInfo(data.pageInfo);
        console.log(pageInfo);
      })
      .catch((err) => console.log(err.message));
  };

  //정렬 메뉴 오픈 핸들러
  const filterHandler = () => {
    setIsOpen(!isOpen);
  };

  const subjectHandler = (e) => {
    if (subjectMenu.includes(e.target.value)) {
      setSubjectMenu(subjectMenu.filter((el) => el !== e.target.value));
    } else {
      setSubjectMenu([e.target.value, ...subjectMenu]);
    }
  };

  const searchHandler = ({ type, key, target }) => {
    if (type === 'change') setSearchValue(target.value);
    else if (target.value === '') setSearch(searchValue);
    else if (type === 'keyup' && key === 'Enter') setSearch(searchValue);
  };

  useEffect(() => {
    getTutorData();
  }, [subjectMenu, search, sort]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.banner}>
          <div className={styles.bannerContents}>
            <span className={styles.bannerText}>
              나에게 꼭 맞는 비대면 과외를 찾아보세요!
            </span>
            <div className={styles.searchBox}>
              <div className={styles.iconBox}>
                <MdSearch className={styles.mdSearch} />
              </div>
              <input
                className={styles.input}
                placeholder="서울대학교"
                value={searchValue}
                onChange={searchHandler}
                onKeyUp={searchHandler}
              ></input>
            </div>
          </div>
        </div>
        <div className={styles.menuContainer}>
          <MenuButtons
            subjectMenu={subjectMenu}
            subjectHandler={subjectHandler}
          />
          <div
            className={styles.filter}
            onClick={filterHandler}
            aria-hidden="true"
          >
            <MdFilterList className={styles.mdFilterList} />
            <span>{sort === 'rate' ? '평점 순' : '최신 순'}</span>
            {isOpen ? <FilterDropdown setFilter={setSort} /> : null}
          </div>
        </div>
        <div className={styles.feedContainer}>
          {tutorData.map((tutor) => (
            <Link
              // ! 프로필 ID 기준으로 링크 변경 필요
              to={`/tutorprofile/${tutor.profileId}`}
              key={tutor.profileId}
              className={styles.list}
            >
              <FeedItem data={tutor} userStatus="TUTOR" />
            </Link>
          ))}
        </div>
      </div>
      <ButtonTop />
    </div>
  );
};

export default TutorList;
