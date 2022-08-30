package hu.gyuriczaadam.sprintformteszt.presentation.add_custom_transaction_screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.gyuriczaadam.sprintformteszt.R
import hu.gyuriczaadam.sprintformteszt.presentation.add_custom_transaction_screen.components.TransparentHintTextField
import hu.gyuriczaadam.sprintformteszt.presentation.common.LocalSpacing
import hu.gyuriczaadam.sprintformteszt.util.TestTags
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditTransactionScreen(
    navController: NavController,
    viewModel: AddEditTransactionViewModel = hiltViewModel()
) {
    val localSpacing = LocalSpacing.current
    val transactionTitleState = viewModel.transactionTitle.value
    val transactionAmountState = viewModel.transactionAmount.value
    val transactionTypeState = viewModel.transactionType.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditTransactionViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditTransactionViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTransactionEvent.SaveTransaction)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = stringResource(R.string.save_costume_transaction))
            }
        },
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(localSpacing.spaceMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.add_edit_screen_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primary
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(localSpacing.spaceLarge)
            ) {

                Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
                TransparentHintTextField(
                    text = transactionTitleState.text,
                    hint = transactionTitleState.hint,
                    imageVector = Icons.Default.Check,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        viewModel.onEvent(AddEditTransactionEvent.EnteredTransactionTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditTransactionEvent.ChangeTransactionTitelFocus(it))
                    },
                    isHintVisible = transactionTitleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
                TransparentHintTextField(
                    text = transactionTypeState.text,
                    hint = transactionTypeState.hint,
                    imageVector = Icons.Default.Check,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        viewModel.onEvent(AddEditTransactionEvent.EnteredTransactionType(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditTransactionEvent.ChangeTransactionTypeFocus(it))
                    },
                    isHintVisible = transactionTypeState.isHintVisible,
                    textStyle = MaterialTheme.typography.body1,
                )

                Spacer(modifier = Modifier.height(localSpacing.spaceMedium))
                TransparentHintTextField(
                    text = transactionAmountState.text,
                    hint = transactionAmountState.hint,
                    imageVector = Icons.Default.Check,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = {
                        viewModel.onEvent(AddEditTransactionEvent.EnteredAmount(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditTransactionEvent.ChangeAmountFocus(it))
                    },
                    isHintVisible = transactionAmountState.isHintVisible,
                    textStyle = MaterialTheme.typography.body1,
                )
            }
        }
    }

}