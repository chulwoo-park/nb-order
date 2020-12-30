# 요구사항 정리

| 화면 | 내용 | 비고 |
| - | - | - |
| 스플래시 | 2초간 화면이 유지된다 | |
| 상품 주문 | 카테고리 정보가 표시된다. | 상단에 표시된다. |
| 상품 주문 | 카테고리를 선택할 수 있다. | 화살표로 이동할 수 있다.<br/>첫번째 카테고리가 기본 선택된다. |
| 상품 주문 | 선택한 카테고리의 상품 목록을 볼 수 있다. | |
| 상품 주문 | 상품을 선택할 수 있다. | 주문서 영역에 추가된다.<br/>이미 있다면 수량이 1 증가한다. |
| 주문서 | 선택한 상품의 정보를 볼 수 있다. | |
| 주문서 | 선택한 상품의 수량을 변경할 수 있다. | |
| 주문서 | 선택한 상품을 제거 할 수 있다. | |
| 주문서 | 선택한 상품 전체를 초기화 할 수 있다. | |
| 주문서 | 총 결제 가격을 볼 수 있다. | 주문서가 갱신될 때 함께 갱신된다. |

# 유즈케이스

요구사항에 따라 다음 유즈케이스를 정의했습니다:

* GetCategories
* GetProducts
* GetCart
* AddToCart
* RemoveFromCart
* DeleteFromCart
* ClearCart

# 뷰 모델

요구사항에 따라 다음 뷰 모델과 상태를 정의했습니다:

* SplashViewModel
  * Initial
  * Success

* CategoryViewModel
  * Initial
  * Loading
  * Success
  * Failure

* ProductViewModel
  * Initial
  * Loading
  * Success
  * Failure

* CartViewModel
  * Initial
  * Loading
  * Success
  * Failure

# Repository

유즈케이스에 따라 다음 Repository를 정의했습니다:

* CategoryRepository
  * get
* ProductRepository
  * get
* CartRepository
  * get
  * add
  * remove
  * delete
